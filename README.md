[![Generate JAR on Release](https://github.com/mohakchavan/general-utils/actions/workflows/deploy-on-release.yaml/badge.svg)](https://github.com/mohakchavan/general-utils/actions/workflows/deploy-on-release.yaml)

# General Utils
This java project provides few of the features which are required by the developers. This project is fully developed
using JAVA language. The main feature of this project is that it runs in offline mode.

## Usage
This project is developed using Java v21. So to run this project directly from the command line, use the v21 of Java
and shaded jar provided in the [release](https://github.com/mohakchavan/general-utils/releases) assets.
```shell
java -jar general-utils-[release-version]-shaded.jar
```

By running the above command will generate the output:<br/><br/>
![Basic-Output](./assets/basic-output.gif)<br/>

Various options are shown in the numbered list in the output when the jar file is run. Choose the feature you want
to run by entering the respective number when prompted. There is also a feature/option to exit the program by
entering "-1" as a feature number when prompted.

## Features
- [GitHub Feature](#github-feature)
- [Logs](#logs)

## GitHub Feature
- This feature encodes the secret value which is to be stored in GitHub.
- For encoding the secret value, public key is used which can be acquired from calling the GitHub's REST API.
- After encoding the secret value, the resulting encoded secret value is returned which can be directly pasted in
the GitHub's API or wherever it is required.

### Example

![GitHub-Encoding-Feature](./assets/github-encoding-feature.gif)

[!NOTE]
Ofcourse, the values shown here are not real and you have to replace them with real values.

## Logs
All the debug logs related to this project are stored in "./general-utils-logs.txt" file.
