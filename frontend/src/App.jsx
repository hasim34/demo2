import { useState } from 'react'
import { Typography, Space, Table, Button } from "antd";
import { Routes, Route, Link } from "react-router-dom";
import Product from './components/Product';

function App() {

  return (
    <>
      <nav>
        <Link to="/products">Show Products</Link>
      </nav>
      <Routes>
        <Route path="/products" element={<Product/>}></Route>
      </Routes>
    </>
  )
}

export default App
