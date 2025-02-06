use jni::{
    objects::{JClass, JObject, JString, JValue},
    sys::jlong,
    JNIEnv,
};
use reqwest::{blocking::Client, Method};

use crate::{
    bridge::{drop_box, pack_box, pull_box},
    utils::JStringToString,
    Error, Result,
};

const REQUEST_BUILDER_CLASS: &str = "net/pixaurora/catculator/impl/http/RequestBuilderImpl";

fn create(env: &mut JNIEnv, user_agent: JString) -> Result<jlong> {
    let client = Client::builder()
        .https_only(true)
        .user_agent(user_agent.to_string(env)?)
        .build()?;

    Ok(pack_box(client))
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_catculator_impl_http_ClientImpl_create<'r>(
    mut env: JNIEnv<'r>,
    _class: JClass<'r>,
    user_agent: JString<'r>,
) -> jlong {
    match create(&mut env, user_agent) {
        Ok(ptr) => return ptr,
        Err(error) => error.throw(&mut env),
    }

    jlong::default()
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_catculator_impl_http_ClientImpl_drop<'r>(
    mut env: JNIEnv<'r>,
    this: JObject<'r>,
) -> () {
    if let Err(error) = drop_box::<Client>(&mut env, &this) {
        panic!("Couldn't drop http client due to an error! {}", error);
    }
}

fn request<'r>(
    env: &mut JNIEnv<'r>,
    this: &JObject<'r>,
    method: JString<'r>,
    url: JString<'r>,
) -> Result<JObject<'r>> {
    let client = pull_box::<Client>(env, this)?;

    let method = match Method::from_bytes(method.to_string(env)?.as_bytes()) {
        Ok(method) => method,
        Err(_) => return Err(Error::String(String::from("Invalid HTTP method."))),
    };

    let ptr = pack_box(client.request(method, url.to_string(env)?));

    let class = env.find_class(REQUEST_BUILDER_CLASS)?;
    let instance = env.new_object(class, "(J)V", &[JValue::Long(ptr)])?;

    Ok(instance)
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_catculator_impl_http_ClientImpl_request0<'r>(
    mut env: JNIEnv<'r>,
    this: JObject<'r>,
    method: JString<'r>,
    url: JString<'r>,
) -> JObject<'r> {
    match request(&mut env, &this, method, url) {
        Ok(object) => return object,
        Err(error) => error.throw(&mut env),
    };

    JObject::null()
}
