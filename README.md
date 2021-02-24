# notes
References:

https://towardsdatascience.com/how-to-generate-parquet-files-in-java-64cc5824a3ce

https://www.baeldung.com/spring-batch-testing-job

https://start.jhipster.tech/

http://websystique.com/spring-security/secure-spring-rest-api-using-basic-authentication/

http://websystique.com/spring-security/secure-spring-rest-api-using-oauth2/

https://github.com/contactsunny/Parquet_File_Writer_POC


Getting Access and Refresh token :
curl -X POST 'http://localhost:8081/oauth/token?grant_type=password&username=pasha2@test.com&password=123456789' -H 'Authorization: Basic bm90ZS1jbGllbnQ6c2VjcmV0'

Get Resource:
http://localhost:8081/api/notes?access_token==@GRANTED_ACCESS_TOKEN


New access token via valid refresh-token :
curl -X POST 'http://localhost:8081/oauth/token?grant_type=refresh_token&refresh_token=@GRANTED_REFERESH_TOKEN' 

