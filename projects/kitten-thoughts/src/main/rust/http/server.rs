use crate::Result;

pub struct Server {
    token_arg_name: String,
}

impl Server {
    pub fn new(token_arg_name: String) -> Self {
        Server { token_arg_name }
    }

    pub fn run_server(&self) -> Result<String> {
        Ok(format!(
            "Wait... this isn't a token! Token argument name is {}",
            self.token_arg_name
        ))
    }
}
