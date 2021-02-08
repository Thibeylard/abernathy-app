/*
    MongoDB
    Description : Initialize database abernathy for patient microservice
 */

conn = new Mongo();

db = conn.getDB("abernathy");

db.createUser({
    user: 'user',
    pwd: 'pass',
    roles: [
        {
            role: 'readWrite',
            db: 'abernathy'
        }
    ]
});