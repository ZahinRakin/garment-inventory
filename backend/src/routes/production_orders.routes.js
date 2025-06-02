import express from 'express';

const router = express.Router();

// Example: Get all production orders
router.get('/', (req, res) => {
  res.json({ message: 'Get all production orders' });
});

// Example: Create a production order
router.post('/', (req, res) => {
  res.json({ message: 'Create a production order' });
});

export default router;