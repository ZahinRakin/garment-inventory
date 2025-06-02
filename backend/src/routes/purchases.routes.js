import express from 'express';

const router = express.Router();

// Example: Get all purchases
router.get('/', (req, res) => {
  res.json({ message: 'Get all purchases' });
});

// Example: Create a purchase
router.post('/', (req, res) => {
  res.json({ message: 'Create a purchase' });
});

export default router;