import express from 'express';

const router = express.Router();

// Example: Get all audit logs
router.get('/', (req, res) => {
  res.json({ message: 'Get all audit logs' });
});

export default router;