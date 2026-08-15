# 🔐 OmniPass

**OmniPass** is an offline-first password manager written in **Java**.

> 🚧 OmniPass is currently under active development and is **not production-ready**.

The `main` branch contains the stable core and CLI implementation. GUI development happens on the `develop` branch.

## ✨ Current Features

### Password management

- Add password entries
- View saved passwords
- Search passwords by website
- Edit password entries
- Delete password entries
- Generate secure random passwords
- Choose a password length when generating a password

### Vault & security

- Master password authentication
- Salted **PBKDF2-HMAC-SHA256** password derivation
- 256-bit AES keys
- Encrypted vault storage
- Random IV generation for vault encryption
- Persistent vault save/load

### Development

- Maven project structure
- Java 26
- JavaFX dependencies configured for the upcoming desktop interface
- MIT licensed

## 🖥️ GUI Status

The desktop GUI is being developed on the [`develop` branch](https://github.com/dakugaming7487/OmniPass/tree/develop).

Current GUI work includes:

- Login screen
- Master password authentication through the existing core
- Dark theme
- Dashboard skeleton
- Search bar UI
- Add Password button UI
- Settings button UI

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 26 | Application language |
| Maven | Build and dependency management |
| JavaFX 26 | Desktop GUI |
| AES | Vault encryption |
| PBKDF2-HMAC-SHA256 | Master password key derivation |
| Git / GitHub | Version control |

## 📁 Project Structure

```text
OmniPass/
├── src/
│   └── main/
│       ├── java/
│       │   ├── core/
│       │   │   ├── crypto/
│       │   │   ├── security/
│       │   │   ├── storage/
│       │   │   ├── utils/
│       │   │   ├── PasswordEntry.java
│       │   │   ├── Vault.java
│       │   │   └── Main.java
│       │   └── gui/
│       │       └── OmniPassApp.java
│       └── resources/
├── docs/
├── pom.xml
├── readme.md
├── todo.md
└── LICENSE
```

## 🚀 Running OmniPass

### Build

```bash
mvn compile
```

### Run the JavaFX application

The full GUI is currently being developed on `develop`.

```bash
mvn javafx:run
```

### CLI

The core password-manager functionality can also be run through the Java CLI while the desktop interface is being developed.

## 🗺️ Roadmap

### v0.2.0 — Core

- [x] CLI password manager
- [x] Add / view / search passwords
- [x] Edit / delete passwords
- [x] Password generator
- [x] Master password authentication
- [x] Encrypted vault storage
- [x] Maven migration

### v0.3.0 — Desktop GUI

- [x] JavaFX application
- [x] GUI login
- [x] Dark theme
- [x] Dashboard foundation
- [ ] Password list UI
- [ ] Add password dialog
- [ ] Edit password dialog
- [ ] Search functionality
- [ ] Settings page
- [ ] Light theme

### v1.0.0 — Stable Release

- [ ] Complete desktop application
- [ ] Comprehensive testing
- [ ] Better error handling
- [ ] Documentation
- [ ] Packaging / distribution
- [ ] Security review

## ⚠️ Security Status

OmniPass is an educational and experimental project at this stage. Do **not** rely on it as your primary password manager for important real-world secrets yet.

The current vault encryption uses AES-CBC. Future security work should move the vault format to an authenticated encryption mode such as AES-GCM and include integrity protection before a stable release.

## 🤝 Contributing

This project is currently developed primarily as a personal learning project. Suggestions, bug reports, and improvements are welcome.

## 🏷️ Previous Names

- **passwd_manager** — original project name

## 📄 License

MIT License — see [`LICENSE`](LICENSE).
