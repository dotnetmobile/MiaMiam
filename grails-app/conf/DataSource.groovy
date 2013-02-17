dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    
    production {
/*		
		dataSource {
//			dialect= org.hibernate.dialect.MySQLInnoDBDialect
			driverClassName = "com.mysql.jdbc.Driver"
//			username = "n/a"
//			password = "n/a"
//			url = "n/a"
			dbCreate = "create-drop"
		}
*/	
		dataSource {
			dialect = org.hibernate.dialect.MySQLInnoDBDialect
			driverClassName = "com.mysql.jdbc.Driver"
			username = "n/a"
			password = "n/a"
			url = "n/a"
			dbCreate = "update"
		}
			
		
    }


}
