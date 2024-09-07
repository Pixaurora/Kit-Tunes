use crate::{Error, Result};
use rocket::{self, get, response::content::RawHtml, routes, Config, Shutdown, State};
use tokio::{
    runtime::Runtime,
    sync::mpsc::{self, Sender},
};

const PORT: u16 = 19686;

const RESPONSE: &str = "
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv=\"refresh\" content=\"30; url=https://files.lostluma.net/VIGK4o.jpg\">
  </head>
  <body>
    <h1>Your account was successfully connected.</h1>
  </body>
</html>";

pub struct Server {
    shutdown: Option<Shutdown>,
}

#[get("/?<token>")]
async fn receive_lastfm_token(sender: &State<Sender<String>>, token: String) -> RawHtml<&str> {
    sender.inner().send(token).await.unwrap();
    RawHtml(RESPONSE)
}

impl Server {
    pub fn new() -> Self {
        Server { shutdown: None }
    }

    pub async fn run_server(&mut self, runtime: &Runtime) -> Result<String> {
        let (sender, mut receiver) = mpsc::channel::<String>(1);

        let server = rocket::build()
            .configure(Config {
                port: PORT,
                ..Config::release_default()
            })
            .manage(sender)
            .mount("/", routes![receive_lastfm_token]);

        let server = server.ignite().await?;
        self.shutdown = Some(server.shutdown());
        let server = runtime.spawn(server.launch());

        let token = receiver.recv().await;

        self.shutdown();
        server.await??;

        match token {
            Some(token) => Ok(token),
            None => Err(Error::Server("Token was not obtained.".into())),
        }
    }

    fn shutdown(&self) {
        if let Some(shutdown) = self.shutdown.to_owned() {
            shutdown.notify();
        }
    }
}

impl Drop for Server {
    fn drop(&mut self) {
        self.shutdown();
    }
}
