# Piecepaper's Cryodex

<img width="100" src="./src/main/resources/cryodex/icon.png" alt="logo">

Cryodex is an open source X-Wing, Imperial Assault, and Armada tournament management software.
It follows and implements the guidelines as described by the Fantasy Flight Tournament Rules for
each of these games.

## Installation

1. Visit the releases page
2. Download the installer for your desktop operating system.
3. Double click and install the application.
4. Start


## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

### Building Cryodex

Maven can be used to build cryodex.  The Maven Wrapper has been provided for convience.

    ./mvn clean install
    
This will build the Cryodex jar from the latest source, you will find the output in the target directory.

With Jpackage you can create a distribution that doesn't require JRE.

    ./sh build.sh

## History
1. 07.Nov.2022 forked and re-released

## Credits


## License

Copyright 2015 Christopher Brown

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.