use std::{
    error::Error,
    fs::{remove_file, File},
    io::Write,
};

use reqwest::blocking::get;

use crate::sound_parsing::audio_length;

const CAT_OGG_URL: &str = "https://stuff.pixaurora.net/cat.ogg";

struct TempFile {
    path: String,
}

impl TempFile {
    pub fn download(url: &str, path: &str) -> Result<TempFile, Box<dyn Error>> {
        let data = get(url)?.bytes()?;

        let mut file = File::create(path)?;

        file.write(&data)?;

        let path = path.into();
        Ok(TempFile { path })
    }

    pub fn path(&self) -> String {
        self.path.to_owned()
    }
}

impl Drop for TempFile {
    fn drop(&mut self) {
        remove_file(self.path.to_owned()).expect("couldn't remove temp file.")
    }
}

#[test]
fn it_works() {
    let cat_ogg_file =
        TempFile::download(CAT_OGG_URL, "cat.ogg").expect("couldn't create temp file");

    let cat_ogg_length = audio_length(cat_ogg_file.path()).expect("couldn't parse cat.ogg");

    assert_eq!(cat_ogg_length.as_millis(), 185343);
}
