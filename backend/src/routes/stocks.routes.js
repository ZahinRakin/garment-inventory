const express = require('express');

const router = express.Router();

// Example route: GET /api/stock
router.get('/stock', (req, res) => {
  res.json({ message: 'Stock management API is working.' });
});

module.exports = router;