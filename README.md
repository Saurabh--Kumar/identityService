# identityService

identity service is a service to generate unique ids. It is fast and horizontaly scalable. In performance test, I found the amortized p99 cost of generating each id to be 68 microseconds. Ids are generated on monotonically increasning order. Uniqueness is guaranteed by the fact that each server in identity service reserves a set of ids and whenever a request comes for generating the ids, it returns the requested number of ids from the reserved set. If the server runs out of ids, It goes back to DB and reserved another set of ids.

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
    
This **policyId** we would use to generate ids.

You can also delete a policy using this curl.

    curl --location --request DELETE 'http://localhost:8080/identityPolicy/delete/1' \
    --header 'Accept: application/json' \
    --header 'Authorization: Basic YTpi' \
    --header 'Content-Type: application/json' \
    --header 'Postman-Token: b9281cbe-2b00-4092-bf31-fbf6bbaeffda,b63a42a9-2339-402a-b2b5-c1b202c8a90d' \
    --header 'cache-control: no-cache,no-cache'
    
## Generating IDs
To generate Id, use the following curl.

    curl --location --request GET 'http://localhost:8080/identity/getId/{poicyId}?batchSize=5' \
    --header 'Accept: application/json' \
    --header 'Authorization: Basic YTpi' \
    --header 'Content-Type: application/json' \
    --header 'Postman-Token: b9281cbe-2b00-4092-bf31-fbf6bbaeffda,b63a42a9-2339-402a-b2b5-c1b202c8a90d' \
    --header 'cache-control: no-cache,no-cache'
    
**policyId** - policyId is the id of the registered policy.
**batchSize** - batchSize is a query parameter to fetch a bunch of Ids in one go. default value for batchSize is 1. Using batchSize, you can fetch multiple ids in one go cache them on your application for later use. For good performance, batchSize <<< fetchSize of policy. If your service needs a lot of Ids, increase the fetchSize in your policy.

## Performance

I ran a performance test on my localhost. on a String policy with fetchSize = 1,000,000 and a batchSize in each request of 1000 for 10 minutes with a throughout of about 3500 requests per seconds (3,500,000 ids per second). The numbers are below. 
    Label,      # Samples,  Average,    Median, 90% Line,   95% Line,   99% Line,   Min,    Max,    Error %,    Throughput, Received KB/sec,    Sent KB/sec
    HTTP Request,2102803,   13,         12,     25,         33,         68,         0,      473,    0.002%,     3486.57055, 108166.9            1,510.72
    

As we can see the p99 is 68 ms. So the amortized p99 to generate 1 id is 68 micro seconds.
