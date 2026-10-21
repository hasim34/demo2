import { useCallback, useEffect, useMemo, useState } from "react";
import axios from "axios";
import {
  Alert,
  Button,
  Card,
  Col,
  Empty,
  Form,
  Input,
  InputNumber,
  Modal,
  Popconfirm,
  Row,
  Select,
  Space,
  Statistic,
  Table,
  Tag,
  Typography,
  message,
} from "antd";

const API_BASE_URL = (import.meta.env.VITE_API_BASE_URL || "http://localhost:8080").replace(/\/$/, "");
const PRODUCTS_URL = `${API_BASE_URL}/api/v1/product`;

const CATEGORY_OPTIONS = [
  { value: "ELECTRONICS", label: "Electronics" },
  { value: "GROCERIES", label: "Groceries" },
  { value: "STATIONARIES", label: "Stationaries" },
];

const formatPrice = (value) =>
  new Intl.NumberFormat("en-IN", {
    style: "currency",
    currency: "INR",
    maximumFractionDigits: 2,
  }).format(Number(value || 0));

function getErrorMessage(error) {
  const data = error?.response?.data;

  if (Array.isArray(data?.errors) && data.errors.length > 0) {
    return data.errors
      .map((item) => `${item.field ? `${item.field}: ` : ""}${item.message || "Invalid value"}`)
      .join("; ");
  }

  return (
    data?.errorMessage ||
    data?.message ||
    data?.error ||
    error?.message ||
    "Something went wrong. Please try again."
  );
}

function Product() {
  const [messageApi, contextHolder] = message.useMessage();
  const [form] = Form.useForm();

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [pageError, setPageError] = useState("");
  const [searchText, setSearchText] = useState("");
  const [selectedCategory, setSelectedCategory] = useState(undefined);
  const [modalOpen, setModalOpen] = useState(false);
  const [saving, setSaving] = useState(false);
  const [editingProduct, setEditingProduct] = useState(null);
  const [adjustmentQuantities, setAdjustmentQuantities] = useState({});
  const [busyProductIds, setBusyProductIds] = useState([]);

  const fetchProducts = useCallback(async () => {
    setLoading(true);
    setPageError("");

    try {
      const response = await axios.get(PRODUCTS_URL);
      setProducts(Array.isArray(response.data) ? response.data : []);
    } catch (error) {
      const text = getErrorMessage(error);
      setPageError(text);
      messageApi.error(`Could not load products: ${text}`);
    } finally {
      setLoading(false);
    }
  }, [messageApi]);

  useEffect(() => {
    fetchProducts();
  }, [fetchProducts]);

  const filteredProducts = useMemo(() => {
    const query = searchText.trim().toLowerCase();

    return products.filter((product) => {
      const matchesCategory = !selectedCategory || product.category === selectedCategory;
      const searchableText = [
        product.id,
        product.category,
        product.manufacturor,
      ]
        .filter((value) => value !== null && value !== undefined)
        .join(" ")
        .toLowerCase();

      return matchesCategory && (!query || searchableText.includes(query));
    });
  }, [products, searchText, selectedCategory]);

  const inventoryStats = useMemo(() => {
    const totalUnits = products.reduce(
      (total, product) => total + Number(product.availableStock || 0),
      0,
    );
    const inventoryValue = products.reduce(
      (total, product) =>
        total + Number(product.price || 0) * Number(product.availableStock || 0),
      0,
    );
    const lowStockCount = products.filter(
      (product) => Number(product.availableStock || 0) <= 5,
    ).length;

    return { totalProducts: products.length, totalUnits, inventoryValue, lowStockCount };
  }, [products]);

  function openCreateModal() {
    setEditingProduct(null);
    form.resetFields();
    form.setFieldsValue({
      category: "ELECTRONICS",
      price: 1,
      availableStock: 0,
      manufacturor: "",
    });
    setModalOpen(true);
  }

  function openEditModal(product) {
    setEditingProduct(product);
    form.setFieldsValue({
      category: product.category,
      price: Number(product.price),
      manufacturor: product.manufacturor,
      availableStock: Number(product.availableStock),
    });
    setModalOpen(true);
  }

  function closeModal() {
    if (saving) return;
    setModalOpen(false);
    setEditingProduct(null);
    form.resetFields();
  }

  async function handleSaveProduct(values) {
    const payload = {
      category: values.category,
      price: Number(values.price),
      manufacturor: values.manufacturor.trim(),
      availableStock: Number(values.availableStock),
    };

    setSaving(true);

    try {
      if (editingProduct) {
        await axios.put(`${PRODUCTS_URL}/${editingProduct.id}`, payload);
        messageApi.success("Product updated successfully.");
      } else {
        await axios.post(PRODUCTS_URL, payload);
        messageApi.success("Product created successfully.");
      }

      setModalOpen(false);
      setEditingProduct(null);
      form.resetFields();
      await fetchProducts();
    } catch (error) {
      messageApi.error(getErrorMessage(error));
    } finally {
      setSaving(false);
    }
  }

  async function handleDeleteProduct(product) {
    try {
      await axios.delete(`${PRODUCTS_URL}/${product.id}`);
      setProducts((current) => current.filter((item) => item.id !== product.id));
      messageApi.success(`Product #${product.id} deleted.`);
    } catch (error) {
      messageApi.error(`Could not delete product: ${getErrorMessage(error)}`);
    }
  }

  function markProductBusy(productId, isBusy) {
    setBusyProductIds((current) => {
      if (isBusy) return current.includes(productId) ? current : [...current, productId];
      return current.filter((id) => id !== productId);
    });
  }

  async function adjustStock(product, direction) {
    const quantity = Math.max(1, Number(adjustmentQuantities[product.id] || 1));
    const currentStock = Number(product.availableStock || 0);
    const delta = direction === "increase" ? quantity : -quantity;
    const nextStock = currentStock + delta;

    if (nextStock < 0) {
      messageApi.warning("You cannot decrement stock below zero.");
      return;
    }

    markProductBusy(product.id, true);

    try {
      const payload = {
        category: product.category,
        price: Number(product.price),
        manufacturor: product.manufacturor,
        availableStock: nextStock,
      };
      const response = await axios.put(`${PRODUCTS_URL}/${product.id}`, payload);
      const savedProduct = response.data;

      setProducts((current) =>
        current.map((item) =>
          item.id === product.id ? { ...item, ...savedProduct, availableStock: nextStock } : item,
        ),
      );
      messageApi.success(`Stock ${direction === "increase" ? "increased" : "decreased"}.`);
    } catch (error) {
      messageApi.error(`Could not update stock: ${getErrorMessage(error)}`);
    } finally {
      markProductBusy(product.id, false);
    }
  }

  const columns = [
    {
      title: "S.NO",
      key: "serialNumber",
      width: 76,
      render: (_value, _record, index) => index + 1,
    },
    {
      title: "Product ID",
      dataIndex: "id",
      key: "id",
      width: 105,
      render: (id) => <Typography.Text code>#{id}</Typography.Text>,
    },
    {
      title: "Category",
      dataIndex: "category",
      key: "category",
      render: (category) => <Tag>{category || "Uncategorised"}</Tag>,
    },
    {
      title: "Manufacturer",
      dataIndex: "manufacturor",
      key: "manufacturor",
      render: (value) => value || "—",
    },
    {
      title: "Price",
      dataIndex: "price",
      key: "price",
      align: "right",
      render: (price) => formatPrice(price),
    },
    {
      title: "Available Stock",
      dataIndex: "availableStock",
      key: "availableStock",
      align: "center",
      render: (stock) => {
        const value = Number(stock || 0);
        const color = value === 0 ? "red" : value <= 5 ? "orange" : "green";
        return <Tag color={color}>{value} units</Tag>;
      },
    },
    {
      title: "Quantity",
      key: "adjustmentQuantity",
      width: 115,
      render: (_value, record) => (
        <InputNumber
          aria-label={`Stock adjustment quantity for product ${record.id}`}
          size="small"
          min={1}
          precision={0}
          value={adjustmentQuantities[record.id] ?? 1}
          onChange={(value) =>
            setAdjustmentQuantities((current) => ({
              ...current,
              [record.id]: Math.max(1, Number(value || 1)),
            }))
          }
        />
      ),
    },
    {
      title: "Decrement",
      key: "decrement",
      width: 115,
      render: (_value, record) => (
        <Button
          size="small"
          disabled={Number(record.availableStock || 0) < Number(adjustmentQuantities[record.id] || 1)}
          loading={busyProductIds.includes(record.id)}
          onClick={() => adjustStock(record, "decrease")}
        >
          − Decrease
        </Button>
      ),
    },
    {
      title: "Increment",
      key: "increment",
      width: 110,
      render: (_value, record) => (
        <Button
          size="small"
          type="primary"
          ghost
          loading={busyProductIds.includes(record.id)}
          onClick={() => adjustStock(record, "increase")}
        >
          + Increase
        </Button>
      ),
    },
    {
      title: "Actions",
      key: "actions",
      fixed: "right",
      width: 155,
      render: (_value, record) => (
        <Space size="small">
          <Button size="small" onClick={() => openEditModal(record)}>
            Edit
          </Button>
          <Popconfirm
            title="Delete this product?"
            description={`Product #${record.id} will be removed.`}
            okText="Delete"
            cancelText="Cancel"
            okButtonProps={{ danger: true }}
            onConfirm={() => handleDeleteProduct(record)}
          >
            <Button size="small" danger>
              Delete
            </Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <div style={{ maxWidth: 1440, margin: "0 auto", padding: "28px 20px" }}>
      {contextHolder}

      <Space orientation="vertical" size="large" style={{ width: "100%" }}>
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            flexWrap: "wrap",
            gap: 16,
          }}
        >
          <div>
            <Typography.Title level={2} style={{ margin: 0 }}>
              Inventory Management
            </Typography.Title>
            <Typography.Text type="secondary">
              View products, manage inventory and adjust stock levels.
            </Typography.Text>
          </div>
          <Space wrap>
            <Button onClick={fetchProducts} loading={loading}>
              Refresh
            </Button>
            <Button type="primary" onClick={openCreateModal}>
              Add Product
            </Button>
          </Space>
        </div>

        {pageError && (
          <Alert
            type="error"
            showIcon
            message="Unable to load inventory"
            description={pageError}
            action={<Button size="small" onClick={fetchProducts}>Retry</Button>}
          />
        )}

        <Row gutter={[16, 16]}>
          <Col xs={24} sm={12} lg={6}>
            <Card>
              <Statistic title="Products" value={inventoryStats.totalProducts} />
            </Card>
          </Col>
          <Col xs={24} sm={12} lg={6}>
            <Card>
              <Statistic title="Total units in stock" value={inventoryStats.totalUnits} />
            </Card>
          </Col>
          <Col xs={24} sm={12} lg={6}>
            <Card>
              <Statistic
                title="Estimated inventory value"
                value={inventoryStats.inventoryValue}
                precision={2}
                prefix="₹"
              />
            </Card>
          </Col>
          <Col xs={24} sm={12} lg={6}>
            <Card>
              <Statistic
                title="Low-stock products (≤ 5)"
                value={inventoryStats.lowStockCount}
                valueStyle={{ color: inventoryStats.lowStockCount ? "#d97706" : undefined }}
              />
            </Card>
          </Col>
        </Row>

        <Card>
          <Space
            orientation="horizontal"
            wrap
            size="middle"
            style={{ width: "100%", marginBottom: 20 }}
          >
            <Input.Search
              allowClear
              placeholder="Search product ID or manufacturer"
              value={searchText}
              onChange={(event) => setSearchText(event.target.value)}
              style={{ width: 300, maxWidth: "100%" }}
            />
            <Select
              allowClear
              placeholder="All categories"
              value={selectedCategory}
              options={CATEGORY_OPTIONS}
              onChange={setSelectedCategory}
              style={{ width: 200, maxWidth: "100%" }}
            />
            <Typography.Text type="secondary">
              Showing {filteredProducts.length} of {products.length} products
            </Typography.Text>
          </Space>

          <Table
            rowKey="id"
            columns={columns}
            dataSource={filteredProducts}
            loading={loading}
            scroll={{ x: 1200 }}
            pagination={{ pageSize: 8, showSizeChanger: true, pageSizeOptions: [8, 16, 32] }}
            locale={{
              emptyText: <Empty description={searchText || selectedCategory ? "No matching products" : "No products yet"} />,
            }}
          />
        </Card>
      </Space>

      <Modal
        title={editingProduct ? `Edit product #${editingProduct.id}` : "Add product"}
        open={modalOpen}
        onCancel={closeModal}
        footer={null}
        destroyOnHidden
      >
        <Form
          form={form}
          layout="vertical"
          requiredMark="optional"
          onFinish={handleSaveProduct}
          style={{ marginTop: 20 }}
        >
          <Form.Item
            label="Category"
            name="category"
            rules={[{ required: true, message: "Please select a category." }]}
          >
            <Select options={CATEGORY_OPTIONS} placeholder="Choose a category" />
          </Form.Item>

          <Form.Item
            label="Manufacturer"
            name="manufacturor"
            rules={[
              { required: true, whitespace: true, message: "Please enter a manufacturer." },
              { min: 3, message: "Manufacturer must have at least 3 characters." },
              { max: 50, message: "Manufacturer cannot exceed 50 characters." },
            ]}
          >
            <Input maxLength={50} placeholder="Enter manufacturer" />
          </Form.Item>

          <Form.Item
            label="Price (₹)"
            name="price"
            rules={[{ required: true, message: "Please enter a price." }]}
          >
            <InputNumber
              min={1}
              precision={0}
              style={{ width: "100%" }}
              placeholder="Enter price"
            />
          </Form.Item>

          <Form.Item
            label="Available stock"
            name="availableStock"
            rules={[{ required: true, message: "Please enter available stock." }]}
          >
            <InputNumber
              min={0}
              precision={0}
              style={{ width: "100%" }}
              placeholder="Enter stock quantity"
            />
          </Form.Item>

          <Space style={{ display: "flex", justifyContent: "flex-end" }}>
            <Button onClick={closeModal} disabled={saving}>
              Cancel
            </Button>
            <Button type="primary" htmlType="submit" loading={saving}>
              {editingProduct ? "Save changes" : "Create product"}
            </Button>
          </Space>
        </Form>
      </Modal>
    </div>
  );
}

export default Product;
