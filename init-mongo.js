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