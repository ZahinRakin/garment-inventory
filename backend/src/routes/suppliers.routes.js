import express from 'express';

const router = express.Router();

// Example: Get all suppliers
router.get('/', (req, res) => {
  res.json({ message: 'Get all suppliers' });
});

// Example: Create a supplier
router.post('/', (req, res) => {
  res.json({ message: 'Create a supplier' });
});

export default router;