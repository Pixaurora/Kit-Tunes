use crate::Result;

use jni::{
    objects::{JClass, JObject, JString},
    sys::jlong,
    JNIEnv,
};

use crate::{bridge::get_pointer, http::server::Server};

fn create<'local>(token_arg_name: JString<'local>, env: &mut JNIEnv<'local>) -> Result<jlong> {
    let token_arg_name: String = env.get_string(&token_arg_name)?.into();

    let server = Server::new(token_arg_name);

    Ok(Box::into_raw(Box::from(server)) as jlong)
}

fn run_server<'local>(
    object: &JObject<'local>,
    env: &mut JNIEnv<'local>,
) -> Result<JString<'local>> {
    let pointer = get_pointer(env, object)?;
    let server = unsafe { &*(pointer as *mut Server) };

    let token = server.run_server()?;

    Ok(env.new_string(token)?)
}

fn drop<'local>(object: &JObject<'local>, env: &mut JNIEnv<'local>) -> Result<()> {
    let pointer = get_pointer(env, object)?;
    #[allow(unused_variables)]
    let server = unsafe { Box::from_raw(pointer as *mut Server) };

    Ok(())
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_kitten_1thoughts_impl_http_server_ServerImpl_create<
    'local,
>(
    mut env: JNIEnv<'local>,
    _class: JClass<'local>,
    token_arg_name: JString<'local>,
) -> jlong {
    match create(token_arg_name, &mut env) {
        Ok(pointer) => pointer,
        Err(b) => {
            b.throw(&mut env);
            jlong::default()
        }
    }
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_kitten_1thoughts_impl_http_server_ServerImpl_runServer0<
    'local,
>(
    mut env: JNIEnv<'local>,
    object: JObject<'local>,
) -> JString<'local> {
    match run_server(&object, &mut env) {
        Ok(token) => token,
        Err(error) => {
            error.throw(&mut env);
            JString::default().into()
        }
    }
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_kitten_1thoughts_impl_http_server_ServerImpl_drop<
    'local,
>(
    mut env: JNIEnv<'local>,
    object: JObject<'local>,
) -> () {
    if let Err(error) = drop(&object, &mut env) {
        error.throw(&mut env);
    }
}
