import { Injectable } from '@nestjs/common';

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
        console.log("New request for game name = "+ gameName);
        const filePath = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=STEAMKEY&format=json";
        //const filePath = "steamJSON/api.steampowered.com.json";
        try {
            const response = await fetch(filePath, {
                headers: {
                    'Accept-Language': 'pt-BR',
                },
            });
    
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
}
