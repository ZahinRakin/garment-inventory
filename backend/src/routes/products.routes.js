import express from 'express';

const router = express.Router();

// Example: Get all products
router.get('/', (req, res) => {
  res.json({ message: 'Get all products' });
});

// Example: Create a product
router.post('/', (req, res) => {
  res.json({ message: 'Create a product' });
});

export default router;