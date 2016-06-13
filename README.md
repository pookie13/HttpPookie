# HttpPookie
Helping Classes that makes Http requests.
It has two simple classes.You can add these classes into your project and start using it.
we have used simple HttpUrlConnection class of android. It provides a bridge which maintains the list of requests in better way and provide appropriate response to your classes(Activity,Fragment and services).

##Steps to use this library:
###1>create an object of HttpPookie class:
 HttpPookie httpPookie = new HttpPookie();

###2>sets values to your request:
 HttpPookie.Request request = httpPookie
                .newRequest()
                .setUrl("http://jsonplaceholder.typicode.com/posts")
                .setType(HttpPookie.POST)
                .build();
                
###3>call execute function and handle response like wise
 httpPookie.newCall(request).execute(this);
 
 Implement these below methods for handling response
 
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
    
    thats it for now.
    further changes will be:
    -set tag id to each and every request.
    -if user cancle request then removes from request list.
    
