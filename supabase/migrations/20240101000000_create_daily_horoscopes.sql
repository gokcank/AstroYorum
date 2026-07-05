-- AstroYorum: Supabase Migration - Daily Horoscopes Table
-- Run this in your Supabase SQL editor

CREATE TABLE IF NOT EXISTS daily_horoscopes (
  date DATE PRIMARY KEY,
  horoscopes JSONB NOT NULL,
  scores JSONB NOT NULL,
  moon_phase JSONB,
  created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Enable read-only access for anonymous users
ALTER TABLE daily_horoscopes ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Allow public read" ON daily_horoscopes
  FOR SELECT USING (true);

-- Service role can insert/update (used by Edge Function)
CREATE POLICY "Allow service role write" ON daily_horoscopes
  FOR ALL USING (auth.role() = 'service_role');
