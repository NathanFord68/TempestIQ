const { MongoClient } = require("mongodb");

const username = process.env.MONGO_USERNAME;
const password = process.env.MONGO_PASSWORD;
const mongo_container_name = process.env.MONGO_CONTAINER_NAME;
const database_name = process.env.DATABASE_NAME;

const uri = `mongodb://${username}:${password}@${mongo_container_name}:27017?authSource=admin`; 

const client = new MongoClient(uri);
await client.connect();

const db = client.db(database_name);
db.createCollection("subscriptions");