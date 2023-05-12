# Example bug semeru + graal.js
demo bug with Semeru & Graal.js

## Context:

console outputs weird characters when eval a simple js script (using console.log).

The temurin  image outputs correctly `Event name: hello` while the semeru one outputs `癅湥⁴慮敭›敨汬੯`

### Run the example using semeru:

- `docker build -f Dockerfile.Semeru -t semeru .`
- `docker run --rm -ti  semeru`

### Run the example using temurin:

- `docker build -f Dockerfile.Temurin -t temurin .`
- `docker run --rm -ti  temurin`

