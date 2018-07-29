# Following can be used to test the services

## Quote
```
http <URI>/v1/quotes?q=PVTL
http <URI>/v1/company/pivotal
```

## Portfolio
```
http <URI>/porfolio/<user_name>
```

## Accounts
```
http <URI>/accounts/<account_id>
```

## Users
```
curl -X POST <URI>/users \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -d '{"id":null,"address":"","passwd":"password","userid":"johndoe2","email":"anon@springsource.com","creditcard":"","fullname":"John Doe","authtoken":null,"creationdate":"2018-07-28T01:31:32.123","logoutcount":null,"lastlogin":null,"logincount":null}'
```
