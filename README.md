# 🌐 Social Network Z: A Modular Monolith

> **A scalable, domain-driven social platform built with Java Spring Boot.**

## 📖 Introduction
Social Network Z is a backend system designed to solve the architectural challenges of scaling a complex domain.

Instead of a traditional Monolith where data is tightly coupled via database joins, this system follows **Strict Modular Boundaries**. It implements **Domain-Driven Design (DDD)** principles to ensure that modules like `User`, `Content`, and `Engagement` are loosely coupled, highly cohesive, and ready to be extracted into Microservices if needed.

## 🏗 System Architecture

The application is structured as a **Modular Monolith**.

* **Vertical Slicing:** Code is organized by **Feature/Domain** (`modules.user`, `modules.content`), not by Technical Layer (`controllers`, `services`).
* **Strict Boundaries:** No `@OneToMany` or `@ManyToOne` relationships across module lines. Cross-module data access is handled via **Public APIs** and **DTOs**.
* **Event-Driven:** Uses **Spring Events** for side effects (e.g., User Deletion cleanup) to ensure eventual consistency without tight coupling.



### Key Design Patterns Implemented
1.  **Assembler Pattern:** Solves the "N+1 Problem" manually by fetching related entities in bulk and stitching them in memory, avoiding cross-module SQL JOINs.
2.  **Rich Domain Models:** Business logic lives in Entities (Aggregates).
3.  **Strategy Pattern:** Used for flexible authentication flows.

---

## 🧩 Modules Breakdown

### 1. 🛡️ Auth Module (`/modules/auth`)
* **Responsibility:** Identity Management, Token Generation.
* **Tech:** Spring Security 6, JWT (RS256).
* **Highlights:** Custom `ArgumentResolvers` for injection-safe user context (`@CurrentUserId`).

### 2. 👤 User Module (`/modules/user`)
* **Responsibility:** User Profiles, Roles, Permissions.
* **Communication:** Exposes a `UserAPI` interface for other modules to validate user existence or fetch profile summaries in bulk.

### 3. 📝 Content Module (`/modules/content`)
* **Responsibility:** Posts, Comments.
* **DDD Structure:** `Post` is the **Aggregate Root**. `Comment` is an entity within the Post aggregate.
* **Constraints:** High integrity (Cascading deletes managed internally).

### 4. ❤️ Engagement Module (`/modules/engagement`)
* **Responsibility:** Upvotes, Downvotes.
* **Design Choice:** Separated from Content to handle high-write volume.

### 5. 🤝 Relationship Module (`/modules/relationship`)
* **Responsibility:** Follower/Following Graph.

---

## 🛠 Tech Stack

* **Core:** Java 21, Spring Boot 3.2
* **Database:** MySQL
* **Security:** Spring Security, OAuth2 Resource Server
* **Persistence:** Spring Data JPA (Hibernate)
