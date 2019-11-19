package com.example.taskmanager;

import android.content.Context;

import com.amazonaws.Response;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;
public interface MyInterface {

    /**
     * Invoke the Lambda function "AndroidBackendLambdaFunction".
     * The function name is the method name.
     */
    @LambdaFunction
    ResponseClass AndroidBackendLambdaFunction(RequestClass request);


    @LambdaFunction
    ResponseClass doesUserExist(RequestClass request);

    @LambdaFunction
    ResponseClass createUser(RequestClass request);

    @LambdaFunction
    ResponseClass getUser(RequestClass request);

    @LambdaFunction
    ResponseClass doesTaskExist(RequestClass request);//, Context context);

    @LambdaFunction
    ResponseClass createReminder(RequestClass request);

    @LambdaFunction
    ResponseClass getReminder(RequestClass request);


    @LambdaFunction
    ResponseClass addFriend(RequestClass request);



}
