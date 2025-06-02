import express from 'express';

const router = express.Router();

// Example: Get all variants
router.get('/', (req, res) => {
  res.json({ message: 'Get all variants' });
});

// Example: Create a variant
router.post('/', (req, res) => {
  res.json({ message: 'Create a variant' });
});

export default router;