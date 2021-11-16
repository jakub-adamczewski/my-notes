CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE NOTE
    ALTER COLUMN id DROP DEFAULT ,
    ALTER COLUMN id TYPE uuid USING (uuid_generate_v4()),
    ALTER COLUMN id SET DEFAULT uuid_generate_v4();