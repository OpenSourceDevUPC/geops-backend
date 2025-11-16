-- Migration: fix 'recommended' column type for subscriptions
-- This alters the `recommended` column to TINYINT(1) to store boolean values as numeric (0/1)
-- Run this against your MySQL database if Hibernate's ddl-auto=update cannot change the column automatically.

ALTER TABLE subscriptions
  MODIFY COLUMN recommended TINYINT(1) NOT NULL DEFAULT 0;

-- If your column currently contains 'true'/'false' strings, convert them:
-- UPDATE subscriptions SET recommended = CASE WHEN recommended IN ('true','1','t','yes') THEN 1 ELSE 0 END;

