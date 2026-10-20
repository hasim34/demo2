import { useState } from "react";
import { Typography, Space, Button, Table } from "antd";

const tableColumns = [
  {
    title: "S.NO",
    dataIndex : "sno",
    key: "sno",
    render : (_,record,index) => (index = index + 1)
  },
{
  title : "Product Name",
  dataIndex: "name",
  key : "name"
},
{
  title: "Category",
  dataIndex: "category",
  key: "category"
},
{
  title: "Manufacturor",
  dataIndex: "manufacturor",
  key: "manufacturor"
},
{
  title: "Price",
  dataIndex: "price",
  key: "price"
},
{
  title: "Available Stock",
  dataIndex: "availStock",
  key: "availStock"
},
{
  title: "Decrement",
  dataIndex: "decrement",
  key: "decrement"
},
{
  title: "Increment",
  dataIndex: "increment",
  key: "increment"
},
{
  title : "Quantity",
  dataIndex: "quantity",
  key: "quantity"
}]

function Product() {
  return (
    <div style={{ maxWidth: "1100", margin:"32px", padding:"20px" }}>
      <Space size="large" orientation="vertical" style={{ width: "100%" }}>
        <Space orientation="vertical">
          <Typography.Title>Inventory Management</Typography.Title>
          <Typography>List of Products available</Typography>
        </Space>
        <Table rowKey="id" columns={tableColumns} locale={{}}></Table>
      </Space>
    </div>
  );
}

export default Product;
