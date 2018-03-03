# Home Directory Redirector Jenkins Plugin

* Sets the $HOME environment variable to $WORKSPACE
* Intended for Unix agents only
* Optionally, copy files from a `skeleton` directory to $WORKSPACE

## Development

**Build Plugin JPI**

`mvn clean package`

**Launch Local Jenkins Instance**

`mvn hpi:run`
