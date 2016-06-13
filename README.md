# HttpPookie
Helping Classes that makes Http requests.
It has two simple classes.You can add these classes into your project and start using it.
we have used simple HttpUrlConnection class of android. It provides a bridge which maintains the list of requests in better way and provide appropriate response to your classes(Activity,Fragment and services).

##Steps to use this library:
###1>create an object of HttpPookie class:
```sh
 HttpPookie httpPookie = new HttpPookie();
```
###2>sets values to your request:

```sh
 HttpPookie.Request request = httpPookie
           .newRequest()
           .setUrl("http://jsonplaceholder.typicode.com/posts")
           .setType(HttpPookie.POST)
           .build();
 ```
You can add more values to your request as per your convenient:

- Add Connection time out

```sh 
setConnectionTimeOut(TIME_TO_SET);
```
 - Add Read time out
```sh
setReadTimeOut(TIME_TO_SET);
```
- Add Tag to Request

```sh    
 setTag("STring_tag");
  setTag(1);   //int tag
```
 
###3>call execute function and handle response like wise
```sh
 httpPookie.newCall(request).execute(this);
```
 Implement these below methods for handling response

```sh
 
    @Override
    public void onNoInternetConnection() {
//todo do whatever if no internet
    }

    @Override
    public void onUnauthorizedConnection() {
    //todo 
    }

    @Override
    public void onResponseData(JSONObject jsonObject) {
    //your resonse will come here.
        Log.d("respocePookie", jsonObject.toString());

        Toast.makeText(RestActivity.this, "done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
//todo if networks error occured
    }

    @Override
    public void onException(Exception e) {

    }
    
```
    
    thats it for now.
    
### Todos:
    
- set tag id to each and every request.
- if user cancle request then removes from request list.
- Add XMl Requests.
- store all response in cache memory to reflects changes.

for more query please feel free to comments and male me [@piyush.gupta][dill]

[dill]: <mailto:piyush.gupta202390@gmail.com>
    
    
