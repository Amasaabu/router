
PayPal API Clone - Routing Service
Welcome to the PayPal API Clone Routing Service repository! This service plays a pivotal role in the distributed system by routing transaction requests based on merchant-specific configurations. It ensures transactions are processed efficiently and according to the merchant’s preferences.

Overview
The Routing Service is responsible for:

Retrieving and validating routing configurations for merchants.
Determining the appropriate processor or service for each transaction.
Routing transactions securely and efficiently to the designated endpoint.
This repository focuses solely on the Routing Service.

Features
Merchant Configuration Validation

Retrieves and validates routing configurations set by merchants in the Middleware Service.
Dynamic Transaction Routing

Routes transactions to the appropriate processor or service based on merchant-defined rules.
Secure Routing

Ensures secure communication and integrity of transaction data during routing.
Built With
Java Spring Boot: The backend application framework used to build the routing service.
Kubernetes: For container orchestration and managing deployments.
Docker: To containerize the application for consistent deployment.
Helm: To manage application upgrades within the Kubernetes cluster.
Deployment Workflow
The deployment of this application follows the same CI/CD process as the Middleware and Processor Services, utilizing GitHub Actions for automation.

Deployment Flow:
Docker Image Creation

A Docker image of the Routing Service is built and published to DockerHub.
Trigger Kubernetes Upgrade

The published Docker image triggers the Kubernetes cluster to upgrade the Routing Service.
Helm is used to manage and automate these upgrades.
GitHub Actions Workflow

The deployment workflow files can be found in the workflows folder.
