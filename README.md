[![Compile & Test Code](https://github.com/mohakchavan/general-utils/actions/workflows/compile-test-code.yaml/badge.svg?branch=development)](https://github.com/mohakchavan/general-utils/actions/workflows/compile-test-code.yaml)

# General Utils
This java project provides few of the features which are required by the developers. This project is fully developed
using JAVA language. The main feature of this project is that it runs in offline mode.

## Usage
This project is developed using Java v21. So to run this project directly from the command line, use the v21 of Java
and shaded jar provided in the [release](https://github.com/mohakchavan/general-utils/releases) assets.
```shell
java -jar general-utils-[release-version]-shaded.jar
```

By running the above command, following output will be generated:<br/><br/>
![Basic-Output](./assets/basic-output.gif)<br/>

## Features
Various features/options are shown in the numbered list in the output when the jar file is run. Choose the feature
you want to run by entering the respective number when prompted. There is also a feature/option to exit the program
by entering "-1" as a feature number when prompted.

The features which are currently implemented are as follows:
- [GitHub Feature](#github-feature)
- [Logs](#logs)

## GitHub Feature
- This feature encodes the secret value which is to be stored in GitHub.
- For encoding the secret value, public key is used which can be acquired from calling the GitHub's REST API.
- After encoding the secret value, the resulting encoded secret value is returned which can be directly pasted in
the GitHub's API or wherever it is required.

### Example

![GitHub-Encoding-Feature](./assets/github-encoding-feature.gif)

> [!NOTE]
> Ofcourse, the values shown here are sample values and you have to replace them with real values.

## Logs
All the debug logs related to this project are stored in "./general-utils-logs.txt" file. The logs are appended if
the log file already exists.

### Sample
```text
2026-04-28T11:04:04.595+0000 [main] DEBUG io.github.mohakchavan.GeneralUtils -- activeProfile: release
2026-04-28T11:10:45.805+0000 [main] DEBUG io.github.mohakchavan.GeneralUtils -- activeProfile: release
2026-04-28T11:11:05.155+0000 [main] ERROR io.github.mohakchavan.GithubFeature -- Invalid Base64 public key. Stacktrace: 
java.lang.IllegalArgumentException: Last unit does not have enough valid bits
	at java.base/java.util.Base64$Decoder.decode0(Base64.java:872)
	at java.base/java.util.Base64$Decoder.decode(Base64.java:570)
	at java.base/java.util.Base64$Decoder.decode(Base64.java:593)
	at io.github.mohakchavan.GithubFeature.decodePublicKey(GithubFeature.java:94)
	at io.github.mohakchavan.GithubFeature.encryptSecretWithKey(GithubFeature.java:72)
	at io.github.mohakchavan.GithubFeature.startGithubFeature(GithubFeature.java:45)
	at io.github.mohakchavan.GeneralUtils.parseFeatureNumber(GeneralUtils.java:80)
	at io.github.mohakchavan.GeneralUtils.main(GeneralUtils.java:49)
2026-04-28T11:25:05.766+0000 [main] DEBUG io.github.mohakchavan.GeneralUtils -- activeProfile: release
2026-04-28T11:27:04.973+0000 [main] DEBUG io.github.mohakchavan.GeneralUtils -- activeProfile: release
```
> [!NOTE]
> The timestamp in the logs are in UTC.
