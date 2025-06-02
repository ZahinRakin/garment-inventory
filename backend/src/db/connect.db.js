import { Pool } from 'pg';

const pool = new Pool({
  user: process.env.PGUSER || 'postgres',
  host: process.env.PGHOST || 'localhost',
  database: process.env.PGDATABASE || 'garment_inventory',
  password: process.env.PGPASSWORD,
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

export { pool, connectDB }; //pool will be used in other files to run queries