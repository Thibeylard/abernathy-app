conn = new Mongo();

db = conn.getDB("mediscreen");

db.createUser({
    user: 'user',
    pwd: 'pass',
    roles: [ 
    	{
    		role: 'readWrite',
    		db: 'mediscreen'
    	}
    ]
});