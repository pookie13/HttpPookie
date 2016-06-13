# HttpPookie
Helping Classes that used to make Http requests.
just have simple two classes add these classes into your project and start using this.
we used simple HttpUrlConnection class of android and just maintains the list of request in better way.
##for using this library you just have to do three steps:
###1>create object of HttpPookie class:
 HttpPookie httpPookie = new HttpPookie();
###2>sets values to your request:
 HttpPookie.Request request = httpPookie
                .newRequest()
                .setUrl("http://jsonplaceholder.typicode.com/posts")
                .setType(HttpPookie.POST)
                .build();
###3>call execute function and handle response like wise
 httpPookie.newCall(request).execute(this);
 
 
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
    
    thats it with it.
    now for further changes will be:
    -set tag id to each and every request.
    -if user cancle request then removes from request list.
    -
