import express from 'express';

const router = express.Router();

// Example: Get all users
router.get('/', (req, res) => {
  res.json({ message: 'Get all users' });
});

// Example: Create a user
router.post('/', (req, res) => {
  res.json({ message: 'Create a user' });
});

export default router;