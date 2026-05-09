-- Add type column to artists, defaulting to SOLO for existing rows
ALTER TABLE artists
  ADD COLUMN `type` VARCHAR(50) NOT NULL DEFAULT 'SOLO';

-- If you prefer to set values manually before enforcing NOT NULL, adjust accordingly.
