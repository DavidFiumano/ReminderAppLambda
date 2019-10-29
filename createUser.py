import json
import boto3

def lambda_handler(event, context):
    # TODO implement
    
    client = boto3.resource('dynamodb')
    
    userTable = client.Table('Reminder_App_Users')
    
    print("Printing table_status now:")
    print(userTable.table_status)
    
    userToAdd = User()
    userToAdd.email = 'email@email.com'
    userToAdd.firstName = 'John'
    userToAdd.lastName = 'Smith'
    
    tableEntry = {userToAdd.email : userToAdd}
    
    response = userTable.put_item(Item = tableEntry)
    print(response)
    
    return {
        'statusCode': 200,
        'body': json.dumps('Hello from Lambda!')
    }
