import { Pool } from 'pg';

const pool = new Pool({
  user: process.env.PGUSER || 'your_db_user',
  host: process.env.PGHOST || 'localhost',
  database: process.env.PGDATABASE || 'your_db_name',
  password: process.env.PGPASSWORD || 'your_db_password',
  port: process.env.PGPORT || 5432,
});

async function connectDB() {
  try {
    const client = await pool.connect();
    console.log('Connected to PostgreSQL database');
    client.release();
  } catch (err) {
    console.error('Failed to connect to PostgreSQL database:', err);
    process.exit(1);
  }
}

module.exports = { pool, connectDB };