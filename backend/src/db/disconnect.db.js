import { Pool } from 'pg';

async function disconnectDB() {
  try {
    await pool.end();
    console.log('Disconnected from PostgreSQL database');
  } catch (err) {
    console.error('Error disconnecting from PostgreSQL database:', err);
  }
}

export { disconnectDB };