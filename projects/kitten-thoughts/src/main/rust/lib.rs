use jni::{
    objects::{JClass, JString},
    sys::jstring,
    JNIEnv,
};

pub mod hello;

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_kitten_1thoughts_scrobbler_ScrobblerSetup_hello<
    'local,
>(
    mut env: JNIEnv<'local>,
    _class: JClass<'local>,
    input: JString<'local>,
) -> jstring {
    let input: String = env
        .get_string(&input)
        .expect("Couldn't get java string!")
        .into();

    let output = hello::hello(input);
    let output = env
        .new_string(output)
        .expect("Couldn't create java string!");

    output.into_raw()
}

#[cfg(test)]
mod tests {
    use super::hello::hello;

    #[test]
    fn hello_message() {
        let output = hello("Coolcat".into());

        assert_eq!(output, "Hello, Coolcat!");
    }
}
