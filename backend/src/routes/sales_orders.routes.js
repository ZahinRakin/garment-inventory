import express from 'express';

const router = express.Router();

// Example: Get all sales orders
router.get('/', (req, res) => {
  res.json({ message: 'Get all sales orders' });
});

// Example: Create a sales order
router.post('/', (req, res) => {
  res.json({ message: 'Create a sales order' });
});

export default router;