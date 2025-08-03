#!/usr/bin/env python3

import os
import re
import glob

# Directory to process
base_dir = "frontend/src"

# Files to exclude (already fixed or config files)
exclude_files = [
    "config/api.ts",
    "components/auth/LoginForm.tsx", 
    "components/auth/RegistrationForm.tsx",
    "components/products/SalesReportPage.tsx",
    "components/products/SalesOrderList.tsx", 
    "components/products/ReportPage.tsx",
    "components/products/ProductList.tsx"
]

def fix_axios_imports(file_path):
    """Fix axios imports and usage in a TypeScript file"""
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        original_content = content
        
        # Step 1: Replace axios import with api import
        content = re.sub(
            r"import axios from ['\"]axios['\"];",
            "import { api } from '../../config/api';",
            content
        )
        
        # Step 2: Replace axios.get/post/put/delete calls with api calls
        # Remove headers with Authorization
        content = re.sub(
            r"axios\.(get|post|put|patch|delete)\(([^,]+),\s*\{\s*headers:\s*\{\s*Authorization:\s*`Bearer \$\{[^}]+\}`\s*\}\s*\}\)",
            r"api.\1(\2)",
            content
        )
        
        # Replace simple axios calls
        content = re.sub(r"axios\.", "api.", content)
        
        # Step 3: Remove unused getStoredAuth import if present
        if "getStoredAuth" in content and "${getStoredAuth" not in content:
            content = re.sub(
                r"import \{ ([^}]*), getStoredAuth \} from ['\"][^'\"]*['\"];",
                r"import { \1 } from '../../utils/auth';",
                content
            )
            content = re.sub(
                r"import \{ getStoredAuth \} from ['\"][^'\"]*['\"];\n",
                "",
                content
            )
            content = re.sub(
                r"import \{ getStoredAuth, ([^}]*) \} from ['\"][^'\"]*['\"];",
                r"import { \1 } from '../../utils/auth';",
                content
            )
        
        # Only write if content changed
        if content != original_content:
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
            print(f"Fixed: {file_path}")
            return True
        return False
        
    except Exception as e:
        print(f"Error processing {file_path}: {e}")
        return False

def main():
    # Find all .tsx files
    pattern = os.path.join(base_dir, "**", "*.tsx")
    tsx_files = glob.glob(pattern, recursive=True)
    
    fixed_count = 0
    
    for file_path in tsx_files:
        # Convert to relative path for checking exclusions
        rel_path = os.path.relpath(file_path, base_dir).replace(os.sep, '/')
        
        if any(excluded in rel_path for excluded in exclude_files):
            print(f"Skipping excluded file: {rel_path}")
            continue
            
        # Check if file contains axios import
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                content = f.read()
                if "import axios from" in content:
                    if fix_axios_imports(file_path):
                        fixed_count += 1
        except Exception as e:
            print(f"Error reading {file_path}: {e}")
    
    print(f"\nCompleted! Fixed {fixed_count} files.")

if __name__ == "__main__":
    main()
