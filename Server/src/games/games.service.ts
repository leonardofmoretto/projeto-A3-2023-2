import { Injectable } from '@nestjs/common';
import * as fs from 'fs';   //modulo padrao para ler arquivos (fs = file system)
@Injectable()
export class GamesService {
    async findOneById(id: number) {
        try {
            console.log("New request for game ID="+id);
            const response = await fetch(`https://store.steampowered.com/api/appdetails?appids=${id}`, {
                headers: {
                    'Accept-Language': "pt-BR",
                },
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const data = await response.json();
            return data;
        } catch (error) {
            console.error(error);
            throw new Error('Error fetching game details');
        }
    }

    async findOneByName(gameName: string) {
        console.log("New request for game name = " + gameName);
    
        const filePath = 'src/games/steamJSON/api.steampowered.com.json';
    
        try {
            const fileData = await fs.promises.readFile(filePath, 'utf-8');
            const jsonData = JSON.parse(fileData);
    
            const matchingObjects = jsonData.applist.apps.filter(app => app.name.toLowerCase().includes(gameName.toLowerCase()));
    
            return matchingObjects;
        } catch (error) {
            console.error('Error reading or parsing JSON file:', error);
            throw new Error('Error fetching game list');
        }
    }

    //se quiser pegar direto da api da steam (mais lento)
    /*
    async findOneByName(gameName: string) {
        console.log("New request for game name = "+ gameName);
        const filePath = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=STEAMKEY&format=json";
  
        try {
            const response = await fetch(filePath);
    
            if (!response.ok) {
                throw new Error(`Failed to fetch ${filePath}: ${response.status} ${response.statusText}`);
            }
    
            const jsonData = await response.json();
    
            const matchingObjects = jsonData.applist.apps.filter(app => app.name.toLowerCase().includes(gameName.toLowerCase()));
            
            return matchingObjects;
        } catch (error) {
            console.error('Error fetching or parsing JSON file:', error);
            throw new Error('Error fetching game list');
        }
    }
    */
}
