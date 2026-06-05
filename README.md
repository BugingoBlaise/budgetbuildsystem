# Budget Build System

A Spring Boot backend application for a construction and building materials marketplace. The system provides a platform for citizens, contractors, suppliers, and administrators to interact in a construction-related ecosystem.

## Project Description

Budget Build System is a multi-role platform designed to facilitate construction project management with the following core functionalities:

- **Citizen**: Register and search for contractors, materials, and building regulations
- **Supplier**: Post and manage construction materials for sale
- **Contractor**: Showcase services and receive reviews/ratings from citizens
- **Administrator**: Manage the system and view comprehensive dashboard analytics

## Features

- JWT-based authentication and authorization
- Role-based access control (CITIZEN, CONTRACTOR, SUPPLIER, ADMIN)
- Material management with image upload support
- Contractor reviews and ratings system
- Building regulations management
- Loan information repository
- Comprehensive reporting dashboard for administrators

## Tech Stack

- **Java 17**
- **Spring Boot 3.3.3**
- **Spring Data JPA** - Database ORM
- **Spring Security** - Authentication and authorization
- **Spring Mail** - Email notifications
- **PostgreSQL** - Primary database
- **JWT (jjwt 0.12.x)** - Token-based authentication
- **Lombok** - Code reduction annotations
- **Maven** - Build tool

## Directory Structure

```
src/
├── main/
│   ├── java/com/budgetbuildsystem/
│   │   ├── BudgetBuildSystemApplication.java    # Main application entry point
│   │   ├── config/
│   │   │   ├── SecurityConfig.java            # Security configuration
│   │   │   ├── MyUserDetailService.java       # User details service
│   │   │   └── InitialRunnerApp.java          # Bootstrap admin user
│   │   ├── controller/
│   │   │   ├── AuthController.java            # Authentication endpoints
│   │   │   ├── MaterialController.java        # Material CRUD endpoints
│   │   │   ├── ContractorsController.java     # Contractor endpoints
│   │   │   ├── RegulationController.java        # Building regulations endpoints
│   │   │   ├── LoanController.java            # Loan information endpoints
│   │   │   ├── UsersController.java           # User management endpoints
│   │   │   ├── DashboardController.java       # Admin dashboard data
│   │   │   └── ReportController.java          # Report generation endpoints
│   │   ├── dto/
│   │   │   ├── SignDto.java                 # Registration request DTO
│   │   │   ├── LoginRequest.java              # Login request DTO
│   │   │   ├── AuthResponse.java              # Authentication response
│   │   │   ├── CitizenDTO.java                # Citizen data transfer
│   │   │   ├── SupplierDto.java               # Supplier DTO
│   │   │   ├── MaterialDto.java               # Material DTO
│   │   │   ├── AdminDashData.java             # Dashboard statistics
│   │   │   ├── GeneralSystemReport.java       # System report DTO
│   │   │   ├── MaterialProcurementReport.java # Material procurement report
│   │   │   └── ContractorPerformanceReport.java # Contractor performance report
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java    # Global error handling
│   │   │   ├── ResourceNotFoundException.java
│   │   │   ├── EntityNotFoundException.java
│   │   │   ├── BuildingRegulationNotFoundException.java
│   │   │   ├── EmailNotFound.java
│   │   │   ├── UserTypeNotFound.java
│   │   │   └── FileUploadExceptionAdvice.java
│   │   ├── filter/
│   │   │   └── JwtAuthFilter.java             # JWT authentication filter
│   │   ├── model/
│   │   │   ├── User.java                      # Base user entity (implements UserDetails)
│   │   │   ├── Citizen.java                   # Citizen profile
│   │   │   ├── Contractor.java                # Contractor profile with ratings
│   │   │   ├── Supplier.java                  # Supplier profile
│   │   │   ├── Administrator.java             # Admin profile
│   │   │   ├── Materials.java               # Construction materials
│   │   │   ├── Loan.java                      # Loan information
│   │   │   ├── Regulations.java              # Building regulations
│   │   │   ├── Recommendation.java            # Contractor reviews
│   │   │   └── enums/
│   │   │       ├── AccountType.java           # User role types
│   │   │       └── EGender.java               # Gender enum
│   │   ├── repository/
│   │   │   ├── IUserRepository.java
│   │   │   ├── ICitizenRepository.java
│   │   │   ├── IContractorRepository.java
│   │   │   ├── ISupplierRepository.java
│   │   │   ├── IAdminRepository.java
│   │   │   ├── IMaterialRepository.java
│   │   │   ├── ILoanRepository.java
│   │   │   ├── IRegulationRepository.java
│   │   │   └── IRecommendationsRepo.java
│   │   ├── service/
│   │   │   ├── authentication/
│   │   │   │   ├── AuthenticationService.java   # Registration and login logic
│   │   │   │   └── EmailService.java          # Email notifications
│   │   │   ├── citizen/CitizenServiceImpl.java
│   │   │   ├── contractor/ContractorServiceImpl.java
│   │   │   ├── supplier/SupplierServiceImpl.java
│   │   │   ├── admin/AdminServiceImpl.java
│   │   │   ├── materials/MaterialServiceImpl.java
│   │   │   ├── loan/LoanServiceImpl.java
│   │   │   ├── regulations/RegulationServiceImpl.java
│   │   │   ├── recommendations/IRecommendationServiceImpl.java
│   │   │   ├── user/UserServiceImpl.java
│   │   │   └── fileService/FileService.java     # File upload handling
│   └── resources/
│       └── application.properties             # Application configuration
└── test/
    └── java/com/budgetbuildsystem/
        └── BudgetBuildSystemApplicationTests.java
```

## Installation

### Prerequisites

- Java 17 or higher
- Maven 3.6+ (or use the included Maven wrapper)
- PostgreSQL 12+ (running locally)

### Database Setup

Create a PostgreSQL database named `project`:

```sql
CREATE DATABASE project;
```

### Build and Run

Using Maven wrapper (recommended):

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Or using Maven directly:

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080` by default.

## Configuration

Configure the application in `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/project
spring.datasource.username=postgres
spring.datasource.password=open123

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# File Upload Configuration
file.upload-dir=uploads/
spring.servlet.multipart.max-file-size=2MB
spring.servlet.multipart.max-request-size=2MB

# Email Configuration (for notifications)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## API Endpoints

### Authentication

| Endpoint | Method | Description | Auth |
|----------|--------|-------------|------|
| `/api/auth/signup` | POST | Register a new user | No |
| `/api/auth/login` | POST | User login | No |
| `/api/auth/logout` | POST | User logout | No |
| `/api/auth/me` | GET | Get current authenticated user | Yes |

### Materials

| Endpoint | Method | Description | Auth |
|----------|--------|-------------|------|
| `/api/materials/public` | GET | Get all materials | No |
| `/api/materials/saveMaterial` | POST | Add new material (supplier only) | Yes (SUPPLIER) |
| `/api/materials/my-materials` | GET | Get supplier's own materials | Yes (SUPPLIER) |
| `/api/materials/updateMaterial/{id}` | PUT | Update a material | Yes |
| `/api/materials/delete/{id}` | DELETE | Delete a material | Yes |
| `/api/materials/search?materialName=` | GET | Search materials by name | No |
| `/api/materials/image/{imageName}` | GET | Get material image | No |

### Contractors

| Endpoint | Method | Description | Auth |
|----------|--------|-------------|------|
| `/api/contractors` | GET | Get all contractors | No |
| `/api/contractors/{contractorId}` | GET | Get contractor by ID | No |
| `/api/contractors/findContractor/{id}` | GET | Find contractor | No |

### Reviews

| Endpoint | Method | Description | Auth |
|----------|--------|-------------|------|
| `/api/reviews/contractors` | GET | List all contractors | No |
| `/api/reviews/contractors/{contractorId}` | GET | Get contractor by ID | No |
| `/api/reviews/review/{contractorId}` | PUT | Rate and review a contractor | Yes |
| `/api/reviews/findByContractorId/{contractorId}` | GET | Get reviews for contractor | No |

### Regulations

| Endpoint | Method | Description | Auth |
|----------|--------|-------------|------|
| `/api/regulations` | GET | Get all regulations | No |
| `/api/regulations/{id}` | GET | Get regulation by ID | No |
| `/api/regulations/saveReg` | POST | Save regulation | Yes |
| `/api/regulations/updateReg/{id}` | PUT | Update regulation | Yes |
| `/api/regulations/deleteReg/{id}` | DELETE | Delete regulation | Yes |
| `/api/regulations/image/{imageName}` | GET | Get regulation image | No |

### Loans

| Endpoint | Method | Description | Auth |
|----------|--------|-------------|------|
| `/api/loans` | GET | Get all loans | No |
| `/api/loans/saveLoan` | POST | Save loan information | Yes |
| `/api/loans/updateLoan/{id}` | PUT | Update loan | Yes |
| `/api/loans/deleteLoan/{id}` | DELETE | Delete loan | Yes |
| `/api/loans/search?loanName=` | GET | Search loans by name | No |
| `/api/loans/image/{imageName}` | GET | Get loan image | No |

### Users (Admin only)

| Endpoint | Method | Description | Auth |
|----------|--------|-------------|------|
| `/api/users` | GET | Get all users | Yes (ADMIN) |
| `/api/users/{Id}` | GET | Get user by ID | Yes (ADMIN) |
| `/api/users/deleteAccount/{id}` | DELETE | Delete user account | Yes (ADMIN) |

### Dashboard & Reports

| Endpoint | Method | Description | Auth |
|----------|--------|-------------|------|
| `/api/admin/dashboard` | GET | Admin dashboard statistics | Yes (ADMIN) |
| `/api/reports/material-procurement` | GET | Material procurement report | Yes (ADMIN) |
| `/api/reports/contractor-performance` | GET | Contractor performance report | Yes (ADMIN) |
| `/api/reports/general-system` | GET | General system report | Yes (ADMIN) |

## Usage Examples

### Register a Contractor

```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_contractor",
    "password": "securePassword123",
    "email": "john@example.com",
    "phoneNumber": "+1234567890",
    "userType": "CONTRACTOR",
    "companyName": "BuildRight Construction",
    "address": "123 Build St",
    "contactDetails": "Available Mon-Fri",
    "licenseNumber": "LIC-2024-001"
  }'
```

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_contractor",
    "password": "securePassword123"
  }'
```

### Add Material (Authenticated Supplier)

```bash
curl -X POST http://localhost:8080/api/materials/saveMaterial \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -F "materialName=Cement" \
  -F "materialDetails=Portland cement 50kg bag" \
  -F "price=15.50" \
  -F "quantity=100" \
  -F "imagePath=@cement.jpg"
```

### Review a Contractor

```bash
curl -X PUT http://localhost:8080/api/reviews/review/{contractorId} \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "rating": 5,
    "reviews": ["Excellent work!", "Highly recommended"]
  }' \
  -G --data-urlencode "citizenId={citizenId}"
```

## Default Admin Credentials

On first run, an admin user is automatically created:

- **Username**: `super.admin`
- **Password**: `open123`
- **Email**: `admin@gmail.com`

## Security

- All passwords are hashed using BCrypt
- JWT tokens are valid for 24 hours
- CORS is configured for `http://localhost:5173`
- Endpoints are protected by role-based authorization

## Development

### Running Tests

```bash
./mvnw test
```

### Building for Production

```bash
./mvnw clean package
```

The JAR file will be generated in `target/budgetbuildsystem-0.0.1-SNAPSHOT.jar`.

## Contribution Guidelines

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Make changes and ensure tests pass
4. Commit changes (`git commit -m 'Add your feature'`)
5. Push to branch (`git push origin feature/your-feature`)
6. Create a Pull Request

### Code Style

- Follow standard Java naming conventions
- Use Lombok annotations to reduce boilerplate code
- Add validation annotations where appropriate
- Handle exceptions gracefully with meaningful error messages

## License

This project is proprietary software for educational purposes.