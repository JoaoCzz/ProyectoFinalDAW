-- Add slug and verified columns to artists table
ALTER TABLE artists ADD COLUMN slug VARCHAR(255);
ALTER TABLE artists ADD COLUMN verified BOOLEAN DEFAULT FALSE;

-- Populate slug from name (simple heuristic). Review duplicates before applying unique constraint.
UPDATE artists SET slug = LOWER(REGEXP_REPLACE(name, '[^a-zA-Z0-9]+', '-', 'g'));

-- Create unique index on slug (this will fail if duplicates exist; clean duplicates first)
CREATE UNIQUE INDEX IF NOT EXISTS uq_artists_slug ON artists(slug);
