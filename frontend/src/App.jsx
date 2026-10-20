import { useState } from 'react'
import { Typography, Space, Table, Button } from "antd";
import { Routes, Route, Link } from "react-router-dom";
import Home from './components/Home';

function App() {

  return (
    <>
      <nav>
        <Link to="/">Home</Link>
      </nav>
      <Routes>
        <Route path="/" element={<Home/>}></Route>
      </Routes>
    </>
  )
}

export default App
