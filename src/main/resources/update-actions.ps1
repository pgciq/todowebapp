# PowerShell script to update all Action files to use DatabaseConfigurationManager

$actionFiles = @(
    "AdminCreateAccountAction.java",
    "AdminReadAccountDetailsAction.java", 
    "UserCreateTaskAction.java",
    "UserReadProfileAction.java",
    "UserReadTaskDetailsAction.java",
    "UserTasksDashboardAction.java",
    "UserUpdateProfileAction.java"
)

$basePath = "src\main\java\io\github\faimoh\todowebapp\actions"

foreach ($file in $actionFiles) {
    $filePath = Join-Path $basePath $file
    Write-Host "Updating $filePath..."
    
    if (Test-Path $filePath) {
        $content = Get-Content $filePath -Raw
        
        # Add import for DatabaseConfigurationManager
        $content = $content -replace "import io\.github\.faimoh\.todowebapp\.dao\.DAOFactory;", 
                   "import io.github.faimoh.todowebapp.dao.DAOFactory;`nimport io.github.faimoh.todowebapp.dao.DatabaseConfigurationManager;"
        
        # Replace hardcoded MySQL DataSource usage
        $content = $content -replace "DAOFactory\.getDAOFactory\(DAOFactory\.MySQLDataSource\)", 
                   "DatabaseConfigurationManager.getDAOFactory()"
        
        Set-Content $filePath $content -NoNewline
        Write-Host "Updated $file"
    } else {
        Write-Host "File not found: $filePath"
    }
}

Write-Host "All Action files updated successfully!"
