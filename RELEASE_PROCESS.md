# Release Process

This document describes the automated release process for the PHP Serialized Object Viewer IntelliJ Plugin.

## Overview

The release process is fully automated with strategic manual checkpoints for quality control:

1. **Manual trigger** - Start release via GitHub Actions
2. **Automated workflows** - Handle version updates, testing, and publishing
3. **Manual review points** - PR review and release publishing

## Release Workflow

### 1. Start Release

**Location**: GitHub Actions Web UI

1. Go to your repository on GitHub
2. Click "Actions" tab
3. Select "Start Release" workflow
4. Click "Run workflow"
5. Enter the new version number (format: `x.y.z`, example: `1.0.5`)
6. Click "Run workflow"

### 2. Automated PR Creation

The workflow will automatically:
- Validate version format
- Check that version doesn't already exist
- Create release branch (`release/v1.0.5`)
- Update version in `gradle.properties`
- Run tests and plugin verification
- Create Pull Request with version changes

### 3. Review and Merge PR

**Manual step required:**
1. Review the automatically created PR
2. Verify all CI checks pass (tests, plugin verification, build)
3. Merge the PR when satisfied

### 4. Automated Release Creation

After PR merge, the system automatically:
- Creates Git tag (`v1.0.5`)
- Generates GitHub Release draft with auto-generated notes
- Cleans up the release branch

### 5. Publish Release

**Manual step required:**
1. Go to GitHub Releases page
2. Find the draft release
3. Edit release notes if needed (optional)
4. Click "Publish release"

### 6. Automated Marketplace Publishing

After release publication, the system automatically:
- Runs final tests and verification
- Publishes plugin to JetBrains Marketplace
- Plugin becomes immediately available to users

## Setup Requirements

### Required Secret

Configure this secret in your GitHub repository settings:

**Repository Settings → Secrets and variables → Actions**

| Secret Name | Value |
|-------------|-------|
| `INTELLIJ_PLATFORM_PUBLISHING_TOKEN` | Your JetBrains Marketplace Personal Access Token |

### Getting Your Token

1. Visit [JetBrains Marketplace](https://plugins.jetbrains.com/)
2. Log in with your JetBrains account
3. Go to [My Tokens](https://plugins.jetbrains.com/author/me/tokens)
4. Click "Generate Token"
5. Provide a name (e.g., "GitHub Actions")
6. Copy the generated token
7. Add to GitHub repository secrets

### Initial Plugin Upload

**Important**: The first plugin version must be uploaded manually to JetBrains Marketplace. After that, all subsequent releases will be automated.

## Quality Checks

The CI pipeline includes comprehensive validation:

- **Multi-Java Testing**: Tests run on Java 17 and 21
- **Plugin Verification**: Validates plugin structure and compatibility
- **Build Validation**: Ensures plugin builds correctly
- **Version Consistency**: Verifies version numbers match across files

## Monitoring Your Release

### GitHub Actions
Monitor progress in the Actions tab:
- Check workflow execution status
- View logs for any issues
- Re-run workflows if needed

### JetBrains Marketplace
After successful publishing:
- Plugin appears in marketplace
- Users receive update notifications
- Download statistics become available

## Troubleshooting

### Common Issues

**Release workflow fails to start:**
- Check version format (must be x.y.z)
- Verify version doesn't already exist as a Git tag

**Tests fail:**
- Review test output in GitHub Actions
- Fix issues and re-run workflows

**Plugin verification fails:**
- Check plugin.xml format
- Verify IntelliJ Platform compatibility
- Review plugin structure

**Marketplace publishing fails:**
- Verify `INTELLIJ_PLATFORM_PUBLISHING_TOKEN` is set correctly
- Ensure plugin was manually uploaded at least once
- Check token permissions

### Manual Recovery

**If you need to restart a release:**

```bash
# Delete release branch if it exists
git push origin --delete release/v1.0.5

# Delete tag if it was created
git tag -d v1.0.5
git push origin :v1.0.5

# Restart from GitHub Actions
```

**If workflows fail:**
1. Fix the underlying issue
2. Re-run the failed workflow from GitHub Actions
3. Continue from where it left off

## Version Management

- Versions follow semantic versioning (x.y.z)
- Version is stored in `gradle.properties` as `projectVersion`
- Git tags use `v` prefix (e.g., `v1.0.5`)
- Plugin marketplace shows version without prefix

## Release Checklist

Before starting a release:
- [ ] All desired changes are merged to main branch
- [ ] Tests are passing locally
- [ ] Plugin works correctly in test environment
- [ ] Release notes content is planned (can be edited later)

During release:
- [ ] PR created successfully
- [ ] CI checks pass
- [ ] PR merged
- [ ] Release draft created
- [ ] Release published
- [ ] Marketplace publishing completed

## Benefits of This Process

- **Automated Quality Control**: Multiple test and verification stages
- **Consistent Versioning**: Automated version management across all files
- **Safe Rollback**: Each step can be reviewed before proceeding
- **Immediate Availability**: Published plugins are instantly available to users
- **Complete Audit Trail**: Full history of releases and changes

The process balances automation for efficiency with manual checkpoints for quality and control.
