# spring-boot-template

![Version: 0.0.0](https://img.shields.io/badge/Version-0.0.0-informational?style=flat-square) ![Type: application](https://img.shields.io/badge/Type-application-informational?style=flat-square) ![AppVersion: 0.0.0](https://img.shields.io/badge/AppVersion-0.0.0-informational?style=flat-square)

A Helm chart for Kubernetes

## Values

| Key | Type | Default | Description |
|-----|------|---------|-------------|
| env | object | `{"DB_URL":"jdbc:postgresql://postgres:5432/template_db","DB_USERNAME":"postgres"}` | Environment variables configuration |
| env.DB_URL | string | `"jdbc:postgresql://postgres:5432/template_db"` | Database URL for PostgreSQL connection |
| env.DB_USERNAME | string | `"postgres"` | Database username |
| fullnameOverride | string | `"spring-boot-template"` | Override the full name of the chart |
| image | object | `{"pullPolicy":"IfNotPresent","repository":"your-dockerhub-username/spring-boot-template","tag":"latest"}` | Image configuration |
| image.pullPolicy | string | `"IfNotPresent"` | Docker image pull policy |
| image.repository | string | `"your-dockerhub-username/spring-boot-template"` | Docker image repository |
| image.tag | string | `"latest"` | Docker image tag |
| nameOverride | string | `"spring-boot-template"` | Override the name of the chart |
| replicaCount | int | `1` | Number of replicas to deploy |
| resources | object | `{"limits":{"cpu":"500m","memory":"512Mi"},"requests":{"cpu":"250m","memory":"512Mi"}}` | Resource configuration |
| resources.limits.cpu | string | `"500m"` | CPU limit |
| resources.limits.memory | string | `"512Mi"` | Memory limit |
| resources.requests.cpu | string | `"250m"` | CPU request |
| resources.requests.memory | string | `"512Mi"` | Memory request |
| secrets | object | `{"data":{"DB_PASSWORD":"postgres"},"enabled":true,"secretName":"spring-boot-template-secret"}` | Secret configuration for sensitive data |
| secrets.data.DB_PASSWORD | string | `"postgres"` | Database password |
| secrets.enabled | bool | `true` | Enable Kubernetes secrets for sensitive data |
| secrets.secretName | string | `"spring-boot-template-secret"` | Name of the Kubernetes secret |
| service | object | `{"port":80,"targetPort":8080,"type":"ClusterIP"}` | Service configuration |
| service.port | int | `80` | Service port |
| service.targetPort | int | `8080` | Target port for the application |
| service.type | string | `"ClusterIP"` | Kubernetes service type |

----------------------------------------------
Autogenerated from chart metadata using [helm-docs v1.14.2](https://github.com/norwoodj/helm-docs/releases/v1.14.2)
