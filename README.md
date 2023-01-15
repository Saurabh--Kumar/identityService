# identityService

identity service is a service to generate unique ids. It is fast and horizontaly scalable. Ids are generated on monotonically increasning order. Uniqueness is guaranteed by the fact that each server in identity service reserves a set of ids and whenever a request comes for generating the ids, it returns the requested number of ids from the reserved set. If the server runs out of ids, It goes back to DB and reserved another set of ids.

## Registering a policy

To use identity service to generate ids, first you have to register a policy. Below is the curl to register a policy.

    curl --location --request POST 'http://localhost:8080/identityPolicy/create' \
    --header 'Accept: application/json' \
    --header 'Authorization: Basic YTpi' \
    --header 'Content-Type: application/json' \
    --header 'Postman-Token: b9281cbe-2b00-4092-bf31-fbf6bbaeffda,b63a42a9-2339-402a-b2b5-c1b202c8a90d' \
    --header 'cache-control: no-cache,no-cache' \
    --data-raw '{
        "fetchSize": 10,
        "idType": "String",
        "description": "Policy 1"
    }'

**fetchSize** - fetchSize is the size of the set which app server resrves in one go when it runs of new ids. The larger the number, the better for performance. The downside for the larger number is that if the app server goes down, the unused ids which it had reserved are lost.
**idType** - identity service supports 2 types of ids. **String** and **Integer**. String ids are made up of alphanumeric characters and - and _. Integer Ids are positive numbers starting from 0. SO technically speaking, Integer ids are actually whole numbers.
**description** - description is to idenntify the policy. You can create multiple policies for different usecases.

The response looks something like this.

    {
        "status": {
            "statusCode": 102,
            "statusType": "SUCCESS",
            "statusMessage": "SUCCESS"
        },
        "policyId": 2
    }
    
## Generating IDs
To generate Id, use the following curl.

    curl --location --request GET 'http://localhost:8080/identity/getId/1?batchSize=10' \
    --header 'Accept: application/json' \
    --header 'Authorization: Basic YTpi' \
    --header 'Content-Type: application/json' \
    --header 'Postman-Token: b9281cbe-2b00-4092-bf31-fbf6bbaeffda,b63a42a9-2339-402a-b2b5-c1b202c8a90d' \
    --header 'cache-control: no-cache,no-cache'
    


