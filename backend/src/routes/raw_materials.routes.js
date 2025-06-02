import express from 'express';

const router = express.Router();

// Example: Get all raw materials
router.get('/', (req, res) => {
  res.json({ message: 'Get all raw materials' });
});

// Example: Create a raw material
router.post('/', (req, res) => {
  res.json({ message: 'Create a raw material' });
});

export default router;